package com.github.msemitkin.githubstreakwidget;

import org.springframework.stereotype.Service;

@Service
public class ImageService {

    public byte[] createSvg(int number, String text) {
        return """
            <svg xmlns="http://www.w3.org/2000/svg" style="isolation: isolate" width="164px" height="170px"
                 direction="ltr">
                <defs>
                    <mask id="mask_out_ring_behind_fire">
                        <rect width="495" height="195" fill="white"/>
                        <ellipse id="mask-ellipse" cx="82.5" cy="32" rx="10" ry="18" fill="black"/>
                    </mask>
                       
                </defs>
                <g style="isolation: isolate">
                    <rect stroke="#E4E2E2" fill="#FFFEFE" rx="4.5" x="0.5" y="0.5" width="164" height="170"/>
                </g>
                <g style="isolation: isolate">
                    <g transform="translate(82.5,48)">
                        <text x="0" y="32" stroke-width="0" text-anchor="middle" fill="#151515" stroke="none"
                              font-family="&quot;Segoe UI&quot;, Ubuntu, sans-serif" font-weight="700" font-size="28px"
                              font-style="normal" style="font-size: 28px; opacity: 1">
                            %d
                        </text>
                    </g>
                    <g transform="translate(82.5,108)">
                        <text x="0" y="32" stroke-width="0" text-anchor="middle" fill="#FB8C00" stroke="none"
                              font-family="&quot;Segoe UI&quot;, Ubuntu, sans-serif" font-weight="700" font-size="14px"
                              font-style="normal" style="opacity: 1">
                            %s
                        </text>
                    </g>
                    <g mask="url(#mask_out_ring_behind_fire)">
                        <circle cx="82.5" cy="71" r="40" fill="none" stroke="#FB8C00" stroke-width="5"
                                style="opacity: 1"/>
                    </g>
                    <g transform="translate(82.5, 19.5)" stroke-opacity="0"
                       style="opacity: 1">
                        <path d="M -12 -0.5 L 15 -0.5 L 15 23.5 L -12 23.5 L -12 -0.5 Z" fill="none"/>
                        <path d="M 1.5 0.67 C 1.5 0.67 2.24 3.32 2.24 5.47 C 2.24 7.53 0.89 9.2 -1.17 9.2 C -3.23 9.2 -4.79 7.53 -4.79 5.47 L -4.76 5.11 C -6.78 7.51 -8 10.62 -8 13.99 C -8 18.41 -4.42 22 0 22 C 4.42 22 8 18.41 8 13.99 C 8 8.6 5.41 3.79 1.5 0.67 Z M -0.29 19 C -2.07 19 -3.51 17.6 -3.51 15.86 C -3.51 14.24 -2.46 13.1 -0.7 12.74 C 1.07 12.38 2.9 11.53 3.92 10.16 C 4.31 11.45 4.51 12.81 4.51 14.2 C 4.51 16.85 2.36 19 -0.29 19 Z"
                              fill="#FB8C00" stroke-opacity="0"/>
                    </g>
                </g>
            </svg>
            """
            .formatted(number, text)
            .getBytes();
    }
}
